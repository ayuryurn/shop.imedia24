package de.imedia24.shop.exceptions

import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import java.time.ZonedDateTime

@ControllerAdvice
class ExceptionValidation : ResponseEntityExceptionHandler() {


    override fun handleMethodArgumentNotValid(
        ex: MethodArgumentNotValidException,
        headers: HttpHeaders,
        status: HttpStatus,
        request: WebRequest
    ): ResponseEntity<Any> {

        val errors = HashMap<String,String>()

        ex.bindingResult.allErrors.forEach{err->
           var fieldName = (err as FieldError).field
            val defaultMessage = err.defaultMessage
            if (defaultMessage != null) {
                errors[fieldName] = defaultMessage
            }
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors)
    }

    @ExceptionHandler(ProductNotFoundException::class)
   fun productNotFoundExceptionHandler(productNotFound:ProductNotFoundException):ResponseEntity<JsonException>{

       return ResponseEntity.status(HttpStatus.NOT_FOUND).body(JsonException(ZonedDateTime.now(),HttpStatus.NOT_FOUND.toString(),productNotFound.message,HttpStatus.NOT_FOUND.value(),productNotFound.path))
   }


    override fun handleHttpMessageNotReadable(
        ex: HttpMessageNotReadableException,
        headers: HttpHeaders,
        status: HttpStatus,
        request: WebRequest
    ): ResponseEntity<Any> {

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(JsonException(ZonedDateTime.now(),
                HttpStatus.NOT_FOUND.toString(),
                "check your data if it is correct",
                HttpStatus.NOT_FOUND.value(),
                "data type does not much"))
    }
    @ExceptionHandler(ProductAlreadyExist::class)
   fun productAlreadyExist(productAlreadyExist:ProductAlreadyExist):ResponseEntity<Any>{
       return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(productAlreadyExist.message.toString())
   }


//    override fun handleHttpMessageNotReadable(
//        ex: HttpMessageNotReadableException,
//        headers: HttpHeaders,
//        status: HttpStatus,
//        request: WebRequest
//    ): ResponseEntity<Any> {
//
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(JsonException(ZonedDateTime.now(),HttpStatus.BAD_REQUEST.toString(),ex.message,HttpStatus.NOT_FOUND.value(),"nothing"))
//    }


}


