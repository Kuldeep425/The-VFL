const Product = require("../models/productModel");
const User=require('../models/UserModel')

const addProduct = async(req, res) => {
  const sid=req.params.sid;
  const seller = await User.findById(sid)
  if(!seller) return res.status(404).send("some error occured")
  const newProd = await Product.create({...req.body,})
  if(!newProd)  return res.status(504).send("some error occured")
  let products=seller._doc.myProducts;
  products=[...products,newProd._doc.id]
  User.findByIdAndUpdate(sid,{myProducts:products},function(e,doc){
    if(e) return res.status(400).send("some error occured")
    res.send("added successfully")
  })
};

const removeProduct = (req, res) => {
  const sid=req.params.sid, pid=req.params.pid;
  const seller = await User.findById(sid)
  if(!seller) return res.status(404).send("some error occured")
  const products=seller._doc.myProducts;
  const idx=products.indexOf(pid)
  if(idx> -1){
    products.splice(idx,1);
    User.findByIdAndUpdate(sid,{myProducts:products},function(e,doc){
      if(e) return res.status(400).send("error occured")
      res.send("removed successfully")
    })
    Product.findByIdAndDelete(pid,function(e,doc){
      if(e) return res.status(404).send("product not found")
      res.send("removed successfully")
    })
  }
};

const updateProduct = (req, res) => {};

const viewMyProducts = (req, res) => {};

const productsByCategory = (req, res) => {};

const productsByTag = (req, res) => {};

module.exports = {
  addProduct,
  removeProduct,
  updateProduct,
  viewMyProducts,
  productsByCategory,
  productsByTag,
};
