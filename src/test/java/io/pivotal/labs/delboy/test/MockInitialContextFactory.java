package io.pivotal.labs.delboy.test;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.spi.InitialContextFactory;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

public class MockInitialContextFactory implements InitialContextFactory {

    private static final ThreadLocal<Context> currentContext = ThreadLocal.withInitial(() -> proxy(Context.class, getInvocationHandler()));

    private static <T> T proxy(Class<T> interfaceType, InvocationHandler invocationHandler) {
        return interfaceType.cast(Proxy.newProxyInstance(MockInitialContextFactory.class.getClassLoader(), new Class[]{interfaceType}, invocationHandler));
    }

    private static InvocationHandler getInvocationHandler() {
        return new InvocationHandler() {
            private final Map<String, Object> bindings = new HashMap<>();

            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                switch (method.getName()) {
                    case "bind":
                        bindings.put((String) args[0], args[1]);
                        return null;
                    case "lookup":
                        return bindings.get((String) args[0]);
                    default:
                        throw new UnsupportedOperationException(method.toString());
                }
            }

        };
    }

    @Override
    public Context getInitialContext(Hashtable<?, ?> environment) throws NamingException {
        return currentContext.get();
    }

    public static void clearCurrentContext() {
        currentContext.remove();
    }

}
