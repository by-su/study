

class LAddTwoNumbers {

    class ListNode(var `val`: Int) {
        var next: ListNode? = null
    }

    fun addTwoNumbers(l1: ListNode?, l2: ListNode?): ListNode? {
        val dummy = ListNode(0)
        var current = dummy
        var carry = 0

        var p1 = l1
        var p2 = l2

        while (p1 != null || p2 != null || carry != 0) {
            val val1 = p1?.`val` ?: 0
            val val2 = p2?.`val` ?: 0

            val sum = val1 + val2 + carry
            carry = sum / 10
            val digit = sum % 10

            current.next = ListNode(digit)
            current = current.next!!

            p1 = p1?.next
            p2 = p2?.next
        }

        return dummy.next
    }
}

fun main() {
    val starter = LAddTwoNumbers()

}