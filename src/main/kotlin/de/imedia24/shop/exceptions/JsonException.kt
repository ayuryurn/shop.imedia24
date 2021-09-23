package de.imedia24.shop.exceptions

import java.time.ZonedDateTime

class JsonException(val time:ZonedDateTime,val error:String?,val message:String?,val status:Int?,val path:String?) {
}