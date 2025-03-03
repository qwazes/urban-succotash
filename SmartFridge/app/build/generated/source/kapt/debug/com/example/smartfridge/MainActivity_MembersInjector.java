package com.example.smartfridge;

import com.example.smartfridge.data.MainDb;
import dagger.MembersInjector;
import dagger.internal.DaggerGenerated;
import dagger.internal.InjectedFieldSignature;
import dagger.internal.QualifierMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

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
public final class MainActivity_MembersInjector implements MembersInjector<MainActivity> {
  private final Provider<MainDb> mainDbProvider;

  public MainActivity_MembersInjector(Provider<MainDb> mainDbProvider) {
    this.mainDbProvider = mainDbProvider;
  }

  public static MembersInjector<MainActivity> create(Provider<MainDb> mainDbProvider) {
    return new MainActivity_MembersInjector(mainDbProvider);
  }

  @Override
  public void injectMembers(MainActivity instance) {
    injectMainDb(instance, mainDbProvider.get());
  }

  @InjectedFieldSignature("com.example.smartfridge.MainActivity.mainDb")
  public static void injectMainDb(MainActivity instance, MainDb mainDb) {
    instance.mainDb = mainDb;
  }
}
