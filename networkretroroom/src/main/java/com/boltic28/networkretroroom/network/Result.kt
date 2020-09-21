package com.boltic28.networkretroroom.network

data class Result(
    var humans: List<PostHuman>
)



//{
//    "results" : [
//    {
//        "gender":"female",
//        "name" : {
//        "title":"Miss",
//        "first":"Selma",
//        "last":"Jørgensen"
//    },
//        "location":{
//        "street":{
//        "number":8600,
//        "name":"Porsevej"
//    },
//        "city":"Øster Assels",
//        "state":"Syddanmark",
//        "country":"Denmark",
//        "postcode":90500,
//        "coordinates":{
//        "latitude":"-34.4684",
//        "longitude":"99.6681"
//    },
//        "timezone":{
//        "offset":"+9:30",
//        "description":"Adelaide, Darwin"}
//    },
//        "email":"selma.jorgensen@example.com",
//        "login":{
//        "uuid":"d44edaff-e645-4cda-9635-2c0c373e3e50",
//        "username":"greendog830",
//        "password":"chocha",
//        "salt":"ApwWYPex",
//        "md5":"8be2c11b67ce70720ffacfc81a5fa69b",
//        "sha1":"cfeaac0b2a3d4218bfa1a141633283bce3d5b50b",
//        "sha256":"6ff44d2af9a6ad2e3d3677fb3cd39a012d87a2a3d456baa933ee42bc37348b99"
//    },
//        "dob":{
//        "date":"1946-06-09T00:12:54.009Z",
//        "age":74
//    },
//        "registered":{
//        "date":"2016-04-13T02:58:25.223Z",
//        "age":4
//    },
//        "phone":"42928300",
//        "cell":"20220657",
//        "id":{
//        "name":"CPR",
//        "value":"090646-7237"
//    },
//        "picture":{
//        "large":"https://randomuser.me/api/portraits/women/23.jpg",
//        "medium":"https://randomuser.me/api/portraits/med/women/23.jpg",
//        "thumbnail":"https://randomuser.me/api/portraits/thumb/women/23.jpg"
//    },
//        "nat":"DK"
//    }],
//    "info":{
//    "seed":"0260c38adb57f56b",
//    "results":1,
//    "page":1,
//    "version":"1.3"
//}
//}