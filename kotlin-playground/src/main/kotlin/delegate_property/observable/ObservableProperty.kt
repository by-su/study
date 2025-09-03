package delegate_property.observable

import java.beans.PropertyChangeSupport

class ObservableProperty(
    val propName: String,
    var propValue: Int,
    val changeSupport: PropertyChangeSupport
) {

    fun getValue(): Int = propValue
    fun setValue(newValue: Int) {
        val oldValue = propValue
        propValue = newValue
        changeSupport.firePropertyChange(propName, oldValue, newValue)
    }
}

fun main() {
    class Person(
        val name: String,
        age: Int,
        salary: Int
    ): PropertyChangeAware() {
        val _age = ObservableProperty("age", age, changeSupport)
        var age: Int
            get() = _age.getValue()
            set(value) { _age.setValue(value)}
    }

}