const express=require('express');
const router=express.Router();

const mongoose=require('mongoose');




var product=require('./model');
product.methods(['get','put','post','delete']);
product.register(router,'/');

module.exports=router;
