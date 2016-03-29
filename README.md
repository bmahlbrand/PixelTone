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
1. Install Python 2.7 (Make sure the path to python is in your PATH variable)
2. Next you will need to install Visual Studio C++
3. Right now only 2013 Works, you can 
	1. Try installing: https://www.microsoft.com/en-us/download/details.aspx?id=40760
	2. If that doesn't work, install: https://www.microsoft.com/en-gb/download/confirmation.aspx?id=44914
4. Update NPM with (npm install npm)
5. Install node-gyp globally with (npm install -g node-gyp)
	1. If this fails there is something wrong with python or the MS Build Tools
4. Download GTK and extract to C:\GTK  (64 or 32, probably need 64)
	1.   http://ftp.gnome.org/pub/GNOME/binaries/win64/gtk+/2.22/gtk+-bundle_2.22.1-20101229_win64.zip
	2.   http://ftp.gnome.org/pub/GNOME/binaries/win32/gtk+/2.24/gtk+-bundle_2.24.10-20120208_win32.zip
5. Download and install libturbo-jpeg to C:\libturbo-jpeg
	1. https://sourceforge.net/projects/libjpeg-turbo/files/1.4.2/
	2. You want the VC executable (probably x64) libjpeg-turbo-1.4.2-vc64.exe
6.  Build Canvas
	1.  Navigate to Middle-tier/node_modules/color-thief/node_modules/canvas
	2.  Run node-gyp rebuild --msvs_version=2013
		1.  If you have errors it is not detecting GTK or Libturbo or something is messed up
		2.  Check the binding.gyp file for correct directory entries to the above folders.

7.	Right now libturbo is not supported building with MS2015, check for update later
8.	Troubleshooting
	1.	https://github.com/Automattic/node-canvas/wiki/Installation---Windows
	2.	https://github.com/nodejs/node-gyp/issues/629#issuecomment-153196245

###Node
1. Install Node
2. Add the MS API Key to config/development file
3. navigate to middletier folder, type: node index
4. You should now be able to goto localhost:3000 and use the program
	1. You may need to create a tmp folder in middletier/backend root