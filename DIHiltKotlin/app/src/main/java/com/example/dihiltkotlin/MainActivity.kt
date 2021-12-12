package com.example.dihiltkotlin

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject
import javax.inject.Qualifier
import javax.inject.Singleton

@AndroidEntryPoint
class MainActivity: AppCompatActivity(){
    /**
     * Ini disebut Field Injection
     * Class Injection = Constructor Injection (Biasa) = Field Injection
     */
    @Inject
    lateinit var someClass: SomeClass
    @Inject
    lateinit var someClassWithParam: SomeClassWithParam

    /**
     * Interface Injection
     */
    @Impl1
    @Inject
    lateinit var someInterface1: SomeInterface
    @Impl2
    @Inject
    lateinit var someInterface2: SomeInterface

    /**
     * Try using Property
     */

//    val textView5 = findViewById(R.id.textView5) as TextView
    private val textView5 : TextView by lazy {
        findViewById(R.id.textView5)
    }
    private val textView6 : TextView by lazy {
        findViewById(R.id.textView6)
    }


    /**
     * Look: I never declare the parameter in the constructor of the implementation
     * and Look: I never add @Inject on SomeInterface class
     */
    @ParamImpl1
    @Inject
    lateinit var someSingletonInterface1: SomeSingletonInterface

    @ParamImpl2
    @Inject
    lateinit var someSingletonInterface2: SomeSingletonInterface

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val textView1 = findViewById(R.id.textView1) as? TextView
        val textView2: TextView = findViewById(R.id.textView2)
        val textView3 = findViewById(R.id.textView3) as? TextView
        val textView4 = findViewById(R.id.textView4) as? TextView
//        val textView5 = findViewById(R.id.textView5) as TextView

        textView1?.text = someClass.doAThing()
//        textView2.text = "Namanya adalah: ${someClass.doSomeOtherThing().get(1)} -> " + someClass.doSomeOtherThing().get(1)
        textView2?.text = someClassWithParam.doAThing()

        textView3?.text =  someInterface1.doInterfaceThing() //Look: Never declare the parameter in the constructor
        textView4?.text =  someInterface2.doInterfaceThing() //Look: Never declare the parameter in the constructor

        textView5.text =  someSingletonInterface1.doInterfaceThing() //Look: Never declare the parameter in the constructor
        textView6.text =  someSingletonInterface2.doInterfaceThing() //Look: Never declare the parameter in the constructor


    }

}

/**
 * https://developer.android.com/training/dependency-injection/hilt-android#component-lifetimes
 * check on th "drawable/activity_scope.png"
 * test using
 *
 * @Singleton
 * @ActivityRetainedScoped
 * @ActivityScope
 * @FragmentScope
 * @ViewScope
 * @ServiceScope
 */
open class SomeClass @Inject constructor(){
    @Inject
    lateinit var someOtherClass: SomeOtherClass

    fun doAThing(): String{
        return "Look I did a Thing"
    }

    fun doSomeOtherThing(): ArrayList<String>{
        return someOtherClass.doSomeOtherThing();
    }
}
class SomeOtherClass @Inject constructor(){
    fun doSomeOtherThing(): ArrayList<String>{
        var myArray: ArrayList<String> = arrayListOf()
        myArray.add("Bagus")
        myArray.add("Anis")
        myArray.add("Tyat")
        myArray.add("Ibuk")

        return myArray
    }
}

open class SomeClassWithParam @Inject constructor(val str: String){

    fun doAThing(): String{
        return "Look I did a Thing With Param ${str!!} ####"
    }

}


/**
 * Look at this
 */
interface SomeInterface{
    fun doInterfaceThing(): String
}
class SomeImplement1 @Inject constructor(): SomeInterface {
    override fun doInterfaceThing(): String {
        return "Look I did Usual Interface One 1"
    }
}
class SomeImplement2 @Inject constructor(): SomeInterface {
    override fun doInterfaceThing(): String {
        return "Look I did Usual Interface Two 2"
    }
}

/**
 *  mendeklarasikan "Interface Injection"
 *  Without this you can't declare @Injection on Interface
 */
//@InstallIn(ActivityComponent::class)
//@Module
//abstract class MyModuleActivity{
//    @ActivityScoped
//    @Binds
//    abstract fun bindSomeDependendy(someImplement1: SomeImplement1): SomeInterface
//}

@InstallIn(SingletonComponent::class)
@Module
class MyModuleSingletonNoParams{
    @Impl1
    @Singleton
    @Provides
    fun provideSomeInterface1(): SomeInterface{
        return SomeImplement1()
    }

    @Impl2
    @Singleton
    @Provides
    fun provideSomeInterface2(): SomeInterface{
        return SomeImplement2()
    }
}
@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class Impl1

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class Impl2

/**
 * End Option
 */




/**
 * Masalahnya Jika Impelentasinya Menggunakan Parameter
 * Look at "Implement Class with Params"
 */
interface SomeSingletonInterface{
    fun doInterfaceThing(): String
}
class SomeSingletonImplement1 @Inject constructor(val someString: String): SomeSingletonInterface {
    override fun doInterfaceThing(): String {
        return "Look I did INTERFACE 1 Thing -> ${someString}"
    }
}
class SomeSingletonImplement2 @Inject constructor(val someString: String, val someInteger: Integer): SomeSingletonInterface {
    override fun doInterfaceThing(): String {
        return "Look I did INTERFACE 2 Thing -> ${someInteger} and ${someString}"
    }
}



//@InstallIn(ApplicationComponent::class)
@InstallIn(SingletonComponent::class)
@Module
class MyModuleSingleton{
//    @Singleton
//    @Provides
//    fun provideSomeString(): String{
//        return "###AND Provide Some String BOS"
//    }
    //Jika ada dua seperti diatas maka tidak bisa
    @Singleton
    @Provides
    fun provideSomeStringMengisiSecaraOtomatis(): String{
        return "###AND Provide Some String BOS gini"
    }
    @Singleton
    @Provides
    fun provideSembarangInteger(): Integer{
        return 354 as Integer;
    }

    /**
     * Lihat bagaimana provideSomeString masuk secara otomatis ke
     * provideSomeInterface(someString: String)
     *
     * and now
     * You can declare interface without @Inject
     */
    @ParamImpl1
    @Singleton
    @Provides
    fun provideSomeInterface1(someString: String): SomeSingletonInterface{
        return SomeSingletonImplement1(someString) //diisi secara otomatis
    }

    @ParamImpl2
    @Singleton
    @Provides
    fun provideSomeInterface2(someString: String, someInteger: Integer): SomeSingletonInterface{
        return SomeSingletonImplement2(someString, someInteger) //diisi secara otomatis
    }

    /**
     * You cant' use above without parameter
     */
//    @Singleton
//    @Provides
//    fun provideSomeInterface(): SomeInterface{
//        return SomeImplement
//    }

}


@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ParamImpl1;

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ParamImpl2;

