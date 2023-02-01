package doro.android.core.util

@Suppress("UNCHECKED_CAST")
fun HashMap<String, out Any?>.filterValueNotNull(): HashMap<String, Any> {
    return this.filter { it.value != null } as HashMap<String, Any>
}
