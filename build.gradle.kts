import org.gradle.api.provider.Property
import org.gradle.api.provider.ValueSource
import org.gradle.api.provider.ValueSourceParameters

abstract class MyValueSource : ValueSource<String, MyValueSource.Parameters> {

    interface Parameters : ValueSourceParameters {
        val value: Property<String>
        val defaultValue: Property<String>
    }

    override fun obtain(): String {
        return parameters.value.getOrNull() ?: parameters.defaultValue.get()
    }
}

tasks.register("myTask") {
    var defaultValue = providers.of(MyValueSource::class) {
        parameters.value.set("hello world")
    }
    var value = providers.of(MyValueSource::class) {
        parameters.defaultValue.set(defaultValue)
    }

    doLast {
        println(value.get())
    }
}
