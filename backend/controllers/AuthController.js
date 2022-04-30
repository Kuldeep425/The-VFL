const User = require("../models/UserModel");
const Token = require("../models/confirmationModel");
const { google } = require("googleapis");
const OAuth2 = google.auth.OAuth2;
const jwt = require("jsonwebtoken");
require("dotenv").config();

const nodemailer = require("nodemailer");

const createTransporter = async () => {
  const oauth2Client = new OAuth2(
    process.env.CLIENT_ID,
    process.env.CLIENT_SECRET,
    "https://developers.google.com/oauthplayground"
  );

  oauth2Client.setCredentials({
    refresh_token: process.env.REFRESH_TOKEN,
  });

  const accessToken = await new Promise((resolve, reject) => {
    oauth2Client.getAccessToken((err, token) => {
      if (err) {
        reject();
      }
      resolve(token);
    });
  });

  const transporter = nodemailer.createTransport({
    service: "gmail",
    auth: {
      type: "OAuth2",
      user: process.env.ADMIN_MAIL,
      accessToken,
      clientId: process.env.CLIENT_ID,
      clientSecret: process.env.CLIENT_SECRET,
      refreshToken: process.env.REFRESH_TOKEN,
    },
  });

  return transporter;
};

// const createToken=(id)=>{       //function to create jwt cookies
//     return jwt.sign({id}, JWT_SECRET,{
//         expiresIn:3*24*60*60*1000
//     })
// }

//register ->
const register = async (req, res) => {
  try {
    const characters =
      "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    let code = "";
    for (let i = 0; i < 25; i++) {
      code += characters[Math.floor(Math.random() * characters.length)];
    }
    const user = await User.create({ ...req.body, confirmationCode: code });
    if (!user)
      return res
        .status(500)
        .json({ success: false, message: "please try again later..." });
    const tokenwork = await Token.create({
      user: user._id,
      confirmationToken: code,
    });

    if (!tokenwork) console.log("token not created");
    else console.log("token created");

    let emailTransporter = await createTransporter();
    emailTransporter
      .sendMail({
        from: process.env.ADMIN_MAIL,
        to: req.body.email,
        subject: "Please confirm your account",
        html: `<h1>Email Confirmation</h1>
                <h2>Hello ${req.body.name}</h2>
                <p>Thank you for considering us. Please confirm your email by clicking on the following link</p>
                <a href=${process.env.SERVER_URL}/auth/verify/${code}> Click here</a>
                </div>`,
      })
      .catch((err) => console.log(err));
    res.json({
      success: true,
      message: "We've just sent an email... verify your account",
    });
  } catch (err) {
    // let error = err.message;
    // if (err.code === 11000) error = "Email is already registerd";
    res.status(400).json({
      success: false,
      message: "some error occured...",
    });
  }
};

const confirmEmail = (req, res) => {
  User.updateOne(
    { confirmationCode: req.params.code },
    { verified: true },
    function (err, docs) {
      if (err) {
        console.log(err);
        return res.status(404).json({ success: false });
      } else {
        res.send("<h1>Account verified, please log into the app.</h1>");
      }
    }
  );
};

//login ->
const login = async (req, res) => {
  const { email, password } = req.body;
  try {
    console.log("email = "+ email,password);
    const user = await User.login(email, password);
    if (!user.verified)
      return res
        .status(404)
        .json({ success: false, message: "account not verified" });
    // const token=createToken(user._id)
    // res.cookie('jwtCookie',token,{httpOnly:false, maxAge:3*24*60*60*1000})
    res.json({
      success: true,
      _id: user._doc._id,
      name: user._doc.name,
      email: user._doc.email,
      cart: user._doc.cart,
      products: user._doc.myProducts,
    });
  } catch (err) {
    console.log(err);
    res.status(400).json({ success: false, message: err.message });
  }
};

//logout ->
const logout = (req, res) => {
  //   res.cookie("jwtCookie", "", { maxAge: 1 }); //set cookie age 1ms and already removed the data in sessionStorage from frontend
  res.json({ success: true, message: "successfully logged out" });
};

const viewProfile = async (req, res) => {
  const id = req.params.id;
  const user = await User.findById(id);
  if (!user)
    return res.status(404).json({ success: false, message: "user not found" });
  res.json({ ...user._doc, success: true });
};

const updateProfile = (req, res) => {
  const id = req.params.id;
  User.findByIdAndUpdate(id, { ...req.body }, function (e, doc) {
    if (e)
      return res
        .status(404)
        .json({ success: false, message: "some error occured" });
    res.json({ success: true, message: "updated successfully" });
  });
};

module.exports = {
  register,
  login,
  logout,
  confirmEmail,
  updateProfile,
  viewProfile,
};
