var restful=require('node-restful');    //node-restful is a good library and makes it easier for api creation
var mongoose=restful.mongoose;

var productschema=mongoose.Schema({



name:String,
price:Number

});

module.exports=restful.model('Product',productschema);
