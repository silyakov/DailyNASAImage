# DailyNASAImage
This is an android app which uses API from NASA web site to display a new photo from space on a daily basis

NASA site has an APOD - astronomy picture of the day page https://apod.nasa.gov/apod/. This app was built around NASA API https://api.nasa.gov/
to receive and show astronomy picture along with its description which is updated on a daily basis at 12.00PM EST. Idea of the
app was taken from an excellent beginners book "Head first Android development" by Jonathan Simon. Book was published in 2011
and since then API in NASA server was changed. Example in the book uses XML data format to retrieve data from NASA server whether
nowadays NASA uses JSON. That fact gave me an excellent opportunity to get acquainted with JSON data format and start using it in my development.
To get access to the data one has to request for API key to be generated. It is very simple and strightforward operation. Just
sign up with your email and receive and API key right away. PLEASE! DON'T USE MY API KEY!!! It has usage limit which is updated
hourly.
This version of app uses Volley library for access network. Application has only two classes. MainActivity class and NetworkUtils one.
The whole program logic is coded in MainActivity whether NetworkUtils used to build an URL for GET request.
