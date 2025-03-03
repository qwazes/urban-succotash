package com.example.smartfridge;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b7\u0018\u00002\u00020\u0001:\u0004\u0007\b\t\nB\u000f\b\u0004\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006\u0082\u0001\u0004\u000b\f\r\u000e\u00a8\u0006\u000f"}, d2 = {"Lcom/example/smartfridge/NavRoutes;", "", "route", "", "(Ljava/lang/String;)V", "getRoute", "()Ljava/lang/String;", "Analytics", "Home", "Settings", "Shopping_List", "Lcom/example/smartfridge/NavRoutes$Analytics;", "Lcom/example/smartfridge/NavRoutes$Home;", "Lcom/example/smartfridge/NavRoutes$Settings;", "Lcom/example/smartfridge/NavRoutes$Shopping_List;", "app_debug"})
public abstract class NavRoutes {
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String route = null;
    
    private NavRoutes(java.lang.String route) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getRoute() {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c7\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lcom/example/smartfridge/NavRoutes$Analytics;", "Lcom/example/smartfridge/NavRoutes;", "()V", "app_debug"})
    public static final class Analytics extends com.example.smartfridge.NavRoutes {
        @org.jetbrains.annotations.NotNull()
        public static final com.example.smartfridge.NavRoutes.Analytics INSTANCE = null;
        
        private Analytics() {
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c7\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lcom/example/smartfridge/NavRoutes$Home;", "Lcom/example/smartfridge/NavRoutes;", "()V", "app_debug"})
    public static final class Home extends com.example.smartfridge.NavRoutes {
        @org.jetbrains.annotations.NotNull()
        public static final com.example.smartfridge.NavRoutes.Home INSTANCE = null;
        
        private Home() {
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c7\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lcom/example/smartfridge/NavRoutes$Settings;", "Lcom/example/smartfridge/NavRoutes;", "()V", "app_debug"})
    public static final class Settings extends com.example.smartfridge.NavRoutes {
        @org.jetbrains.annotations.NotNull()
        public static final com.example.smartfridge.NavRoutes.Settings INSTANCE = null;
        
        private Settings() {
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c7\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lcom/example/smartfridge/NavRoutes$Shopping_List;", "Lcom/example/smartfridge/NavRoutes;", "()V", "app_debug"})
    public static final class Shopping_List extends com.example.smartfridge.NavRoutes {
        @org.jetbrains.annotations.NotNull()
        public static final com.example.smartfridge.NavRoutes.Shopping_List INSTANCE = null;
        
        private Shopping_List() {
        }
    }
}