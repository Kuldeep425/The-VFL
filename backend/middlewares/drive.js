const { google } = require("googleapis");
const { CLIENT_ID, CLIENT_SECRET, REFRESH_TOKEN } = process.env;
const REDIRECT_URI = "https://developers.google.com/oauthplayground";

const oauth2client = new google.auth.OAuth2(
  CLIENT_ID,
  CLIENT_SECRET,
  REDIRECT_URI
);
oauth2client.setCredentials({ refresh_token: REFRESH_TOKEN });

const drive = google.drive({ version: "v3", auth: oauth2client });
