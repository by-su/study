package delegate_property.observable

import java.beans.PropertyChangeListener
import java.beans.PropertyChangeSupport

open class PropertyChangeAware {
    protected val changeSupport = PropertyChangeSupport(this)

    fun addPropertyChangeListener(listener: PropertyChangeListener) {
        changeSupport.addPropertyChangeListener(listener)
    }

    fun removePropertyChangeListener(listener: PropertyChangeListener) {
        changeSupport.removePropertyChangeListener(listener)
    }
}


fun main() {
    class Person(
        val name: String,
        age: Int,
        salary: Int
    ): PropertyChangeAware() {
        var age: Int = age
            set(newValue) {
                val oldValue = field // 뒷받침하는 필드에 접근할 때 field사용
                field = newValue
                changeSupport.firePropertyChange(
                    "age", oldValue, newValue
                )
            }
    }

    val p = Person("dobby", 20, 50_000)
    p.addPropertyChangeListener { event ->
        println("Property ${event.propertyName} changed " +
                "from ${event.oldValue} to ${event.newValue}")
    }

    p.age = 21

}