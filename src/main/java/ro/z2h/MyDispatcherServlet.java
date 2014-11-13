package ro.z2h;

import org.codehaus.jackson.map.ObjectMapper;
import ro.z2h.annotation.MyController;
import ro.z2h.annotation.MyRequestMethod;
import ro.z2h.controller.DepartmentController;
import ro.z2h.controller.EmployeeController;
import ro.z2h.fmk.AnnotationScanUtils;
import ro.z2h.fmk.MethodAttributes;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Miha on 11/11/2014.
 */
public class MyDispatcherServlet extends HttpServlet{
    public HashMap<String, MethodAttributes> galeata = new HashMap<String, MethodAttributes>();//in mod normal, ar trebui sa o facem
                                    // static,dar pt ca se face doar 1 instanta de dispatcherServlet, nu e nevoie sa mai punem static

    @Override
    public void init() throws ServletException {
        try {
            Iterable<Class> classes = AnnotationScanUtils.getClasses("ro.z2h.controller");
            for(Class aClass : classes) {
                System.out.println(aClass.getName());
                if(aClass.isAnnotationPresent(MyController.class)) { //verif daca are adnotarea MyController
                    MyController ctrlAnnotation = (MyController) aClass.getAnnotation(MyController.class);
                    System.out.println(ctrlAnnotation.urlPath());

                    /*Ex 19: identificare metode care proceseaza business */
                    Method[] methods = aClass.getMethods();
                    for(Method method : methods) {
                        if(method.isAnnotationPresent(MyRequestMethod.class)) { //verif daca are adnotarea MyRequestMethod
                            MyRequestMethod methodAnnotation = (MyRequestMethod) method.getAnnotation(MyRequestMethod.class);
                            System.out.println(methodAnnotation.urlPath() + " " + methodAnnotation.methodType());

                            MethodAttributes val = new MethodAttributes();
                            val.setControllerClass(aClass.getName());
                            val.setMethodName(method.getName());
                            val.setMethodType(methodAnnotation.methodType());
                            String key = ctrlAnnotation.urlPath() + methodAnnotation.urlPath();
                            val.setMethodParameterType(method.getParameterTypes());
                            galeata.put(key, val);
                        }
                    }
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void dispatchReply(String httpMethod, HttpServletRequest req, HttpServletResponse resp) {
        /* Dispatch - delegare catre un ApplicationController si asteptarea unui raspuns */
        Object r = dispatch(req, resp);

        /* Transmiterea raspunsului catre client */
        try {
            reply(r, req, resp);
        } catch (IOException e) {
            /* Transmiterea erorilor */
            sendException(e, req, resp);
        }
    }

    /* Unde ar trebui apelat un ApplicationController */
    private Object dispatch(HttpServletRequest req, HttpServletResponse resp) {
        String pathInfo = req.getPathInfo(); //url-ul fara parametri

        /* Stabilire controller in functie de pathInfo */
//        if(pathInfo.startsWith("/employee")) {
//            EmployeeController controller = new EmployeeController();
//            return controller.getAllEmployees();
//        }
//        if(pathInfo.startsWith("/department")){
//            DepartmentController controller = new DepartmentController();
//            return controller.getAllDepartments();
//        }
        MethodAttributes methodAttributes = galeata.get(pathInfo);
        req.getParameterMap();
        try {
            if(methodAttributes != null) {
                Class appControllerClass = Class.forName(methodAttributes.getControllerClass());
                Object appControllerInstance = appControllerClass.newInstance();
                Method controllerMethod = appControllerClass.getMethod(methodAttributes.getMethodName(), methodAttributes.getMethodParameterType());

                Parameter[] realParameters = controllerMethod.getParameters();
                List<String> parametersValues = new ArrayList<>();
                String[] methodParamsValues = new String[methodAttributes.getMethodParameterType().length];
                for(Parameter param : realParameters){
                    parametersValues.add(req.getParameter(param.getName()));
                }
                return controllerMethod.invoke(appControllerInstance, (String[]) parametersValues.toArray(new String[0]));
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        return "Hello";
    }

    private void reply(Object r, HttpServletRequest req, HttpServletResponse resp) throws IOException {
        PrintWriter out = resp.getWriter();
        ObjectMapper objectMapper = new ObjectMapper(); //clasa din libraria jackson care se ocupa cu afisarea obiectelor dupa un standard
        String valueAsString = objectMapper.writeValueAsString(r);
        out.printf(valueAsString);
    }

    private void sendException(Exception ex, HttpServletRequest req, HttpServletResponse resp) {
        /* Tratare exceptii */
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        /* Delegate to someone(an ApplicationController) */
        dispatchReply("GET",req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        /* Delegate to someone(an ApplicationController) */
        dispatchReply("POST", req, resp);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        dispatchReply("DELETE", req, resp);
    }
}
