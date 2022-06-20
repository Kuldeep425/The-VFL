const Product = require("../models/productModel");
const User = require("../models/UserModel");

const addProduct = async (req, res) => {
  const sid = req.params.sid;
  console.log(req.body);
  const seller = await User.findById(sid);
  if (!seller)
    return res
      .status(404)
      .json({ success: false, message: "some error occured" });
  const newProd = await Product.create({ ...req.body, sellerId: sid });
  if (!newProd)
    return res
      .status(504)
      .json({ success: false, message: "some error occured" });
  let products = await seller.myProducts;
  products = [...products, await newProd._id];
  User.findByIdAndUpdate(sid, { myProducts: products }, function (e, doc) {
    if (e)
      return res
        .status(400)
        .json({ success: false, message: "some error occured" });
    res.json({ success: true, message: "added successfully" });
  });
};

const removeProduct = async (req, res) => {
  const sid = req.params.sid,
    pid = req.params.pid;
  const seller = await User.findById(sid);
  if (!seller)
    return res
      .status(404)
      .json({ success: false, message: "some error occured" });
  const products = seller.myProducts;
  const idx = products.indexOf(pid);
  if (idx > -1) {
    products.splice(idx, 1);
    User.findByIdAndUpdate(sid, { myProducts: products }, function (e, doc) {
      if (e)
        return res
          .status(400)
          .json({ success: false, message: "some error occured" });
    });
    Product.findByIdAndDelete(pid, function (e, doc) {
      if (e)
        return res
          .status(404)
          .json({ success: false, message: "product not found" });
      res.json({ success: true, message: "removed successfully" });
    });
  }
};

const updateProduct = async (req, res) => {
  const sid = req.params.sid,
    pid = req.params.pid;
  const product = await Product.findById(pid);
  if (!product)
    return res
      .status(404)
      .json({ success: false, message: "some error occured" });
  //Check whether the product owner is updating the product
  if (product.sellerId != sid)
    return res
      .status(401)
      .json({ success: false, message: "unauthorized access" });
  Product.findByIdAndUpdate(pid, { ...req.body }, function (e, doc) {
    if (e)
      return res
        .status(404)
        .json({ success: false, message: "some error occured" });
    res.json({ success: true, message: "successfully updated" });
  });
};

const viewMyProducts = async (req, res) => {
  const sid = req.params.sid;
  const categories = req.query.category;
  const tags = req.query.tag;
  let Products = await Product.find({ sellerId: sid });
  if (!Products)
    return res.status(404).json({ success: false, message: "not found" });
  if (!categories && !tags) return res.json({ products:Products, success: true });
  let ans = [];
  for (let i = 0; i < Products.length; i++) {
    const category = Products[i].categories,
      tag = Products[i].tags;
    if (
      category &&
      categories &&
      category.some((i) => categories.includes(i))
    ) {
      ans = [...ans, Products[i]];
      continue;
    }
    if (tags && tag && tag.some((i) => tags.includes(i))) {
      ans = [...ans, Products[i]];
    }
  }
  res.json({products:ans, success: true });
};

const viewOne = async (req, res) => {
  const pid = req.params.pid;
  const product = await Product.findById(pid);
  if (!product)
    return res.status(404).json({ success: false, message: "not found" });
  res.json({ product:product, success: true });
};

//view all products of a particular category or tag..
const viewall = async (req, res) => {
  const categories = req.query.category;
  const tags = req.query.tag;
  // const sorted=req.query.sorted;
  let Products = await Product.find();
  if (!Products)
    return res.status(404).json({ success: false, message: "not found" });
  if (!categories && !tags) return res.json({ ...Products, success: true });
  let ans = [];
  for (let i = 0; i < Products.length; i++) {
    const category = Products[i].categories,
      tag = Products[i].tags;
    if (
      category &&
      categories &&
      categories.includes(category)
    ) {
      ans = [...ans, Products[i]];
      continue;
    }
    if (tags && tag && tag.some((i) => tags.includes(i))) {
      ans = [...ans, Products[i]];
    }
  }
  res.json({ products:ans, success: true });
};
//view all products by category...
const GetAllSortedByCategory= async (req,res)=>{
  let finalResponse={};
  let Products = await Product.find();
  if (!Products)
    return res.status(404).json({ success: false, message: "not found" });
  for (let i = 0; i < Products.length; i++) {
    const category = Products[i].categories;
    if(!finalResponse[category])
      finalResponse[category]=[Products[i]];
    else
      finalResponse[category]=[...finalResponse[category],Products[i]];
  }
  return res.json({products:finalResponse,success:true});
}

//TODO: make view all by category

module.exports = {
  addProduct,
  removeProduct,
  updateProduct,
  viewMyProducts,
  viewall,
  viewOne,
  GetAllSortedByCategory
};
