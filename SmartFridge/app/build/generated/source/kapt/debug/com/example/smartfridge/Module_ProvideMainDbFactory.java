package com.example.smartfridge;

import android.app.Application;
import com.example.smartfridge.data.MainDb;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava",
    "cast"
})
public final class Module_ProvideMainDbFactory implements Factory<MainDb> {
  private final Provider<Application> appProvider;

  public Module_ProvideMainDbFactory(Provider<Application> appProvider) {
    this.appProvider = appProvider;
  }

  @Override
  public MainDb get() {
    return provideMainDb(appProvider.get());
  }

  public static Module_ProvideMainDbFactory create(Provider<Application> appProvider) {
    return new Module_ProvideMainDbFactory(appProvider);
  }

  public static MainDb provideMainDb(Application app) {
    return Preconditions.checkNotNullFromProvides(Module.INSTANCE.provideMainDb(app));
  }
}
