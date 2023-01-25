package com.example.ertah.DataModels

data class RequestsModel(val description:String=""
                         ,val time:String=""
                         ,val date:String =""
                         ,var isComplete:Boolean=false
                         ,var state:String=""
                         ,val client_phone:String=""
                         ,val worker_phone:String=""
                         ,val rating:Double=0.0
)
