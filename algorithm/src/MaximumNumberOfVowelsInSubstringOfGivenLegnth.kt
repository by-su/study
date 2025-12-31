class MaximumNumberOfVowelsInSubstringOfGivenLegnth {
    fun maxVowels(s: String, k: Int): Int {

        var vowelCount = 0
        for (i in 0 until k) {
            if (isVowel(s[i])) vowelCount++
        }

        var max = vowelCount
        var left = 0
        for (right in k until s.length) {
            if (isVowel(s[left])) vowelCount--
            if (isVowel(s[right])) vowelCount++
            max = Math.max(vowelCount, max)
            left++
        }

        return max

    }

    private fun isVowel(c: Char): Boolean {
        return c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u'
    }
}