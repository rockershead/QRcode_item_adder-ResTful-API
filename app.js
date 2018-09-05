const express=require('express');
const app=express();
const bodyparser=require('body-parser');
const mongoose=require('mongoose');



const productroute=require('./api/product');


mongoose.connect(
"mongodb://127.0.0.1:27017/productdb"









);




app.use(bodyparser.json());


app.use('/product',productroute);




module.exports=app;
