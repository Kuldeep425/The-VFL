const Router = require("express").Router();
const {
  login,
  register,
  confirmEmail,
  updateProfile,
  viewProfile,
} = require("../controllers/AuthController");

Router.post("/login", (req, res) => login(req, res));
Router.post("/register", (req, res) => register(req, res));
Router.get("/verify/:code", (req, res) => confirmEmail(req, res));
Router.post("/update/:id", (req, res) => updateProfile(req, res));
Router.get("/view/:id", (req, res) => viewProfile(req, res));

module.exports = Router;
