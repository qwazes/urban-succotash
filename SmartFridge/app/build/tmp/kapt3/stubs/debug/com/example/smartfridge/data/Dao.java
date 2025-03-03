package com.example.smartfridge.data;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\bg\u0018\u00002\u00020\u0001J\u0014\u0010\u0002\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u00040\u0003H\'J\u0012\u0010\u0006\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u0007\u001a\u00020\bH\'J\u0016\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\f\u00a8\u0006\r"}, d2 = {"Lcom/example/smartfridge/data/Dao;", "", "getAllProducts", "Lkotlinx/coroutines/flow/Flow;", "", "Lcom/example/smartfridge/data/Product;", "getProductsByQr", "qr", "", "insertProduct", "", "product", "(Lcom/example/smartfridge/data/Product;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
@androidx.room.Dao()
public abstract interface Dao {
    
    @androidx.room.Insert()
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object insertProduct(@org.jetbrains.annotations.NotNull()
    com.example.smartfridge.data.Product product, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM products")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.example.smartfridge.data.Product>> getAllProducts();
    
    @androidx.room.Query(value = "SELECT * FROM products WHERE numberQR = :qr")
    @org.jetbrains.annotations.Nullable()
    public abstract com.example.smartfridge.data.Product getProductsByQr(@org.jetbrains.annotations.NotNull()
    java.lang.String qr);
}