package ink.bluecloud.exceptions

class PojoException(json:String,exceptions:Throwable) :Exception("Unresolved POJO Exception!; JSON: ${json}",exceptions)