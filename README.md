# s490-sep - PixelTone


- Santiago Abondano
- Benjamin Ahlbrand
- John Masterson
- Austin Rauschuber
- Jacob Richwine
- Paul Ryan

##Problem Statement:

Taking a picture is as easy as point and shoot. Displaying them is now as easy as 

plugging an SD card into a digital picture frame, pulling them up on your phone, or 

countless other devices. Why isn't finding music to fit those photos this easy? Right 

now, a consumer has to either make music or find the perfect song which is time 

consuming. We want to simplify this choice, by providing a quick and easy way to 

generate music based on factors such as moods or emotions, colors, objects and 

scenes contained within your photos and slideshows.


##Background Information:

There is currently a market gap that can be filled by our project.  While there are several 

programs that will generate music for you based on certain parameters, there are few that utilize 

images to feed the music composition algorithm those parameters.  The current products are 

severally lacking in features, usability, depth, and control over the music generation.  We will be 

utilizing the recent Facial Emotion APIs by Microsoft and others to gather moods and generate 

music from that.  We are unique in this aspect, and greatly expand upon the current 

marketplace for music generation applications.

##Middle-Tier Setup

###Database

1. Install MongoDB
2. Create directory for mongodb "C:\pixeltonedb" or something
3. Navigate to mongodb/bin folder, run mongod --dbpath="C:\pixeltonedb"

###Configure Computer for Graphic Parsing
1. Install ImageMagic (ImageMagick-6.9.3-6-Q16-x64-dll.exe) from: http://www.imagemagick.org/script/binary-releases.php

###Node
1. Install Node
2. Add the MS API Key to config/development file
3. navigate to middletier folder, type: node server
4. You should now be able to goto localhost:3000 and use the program
	1. You may need to create a tmp folder in middletier/backend root