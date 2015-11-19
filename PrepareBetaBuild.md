# Introduction #

These are the instructions for creating a new alpha/beta build of Hoi Polloi. The following steps are done in Linux.


# Details #

  1. Create a build directory (eg. ~/HPBeta)
  1. Change into the build directory.
  1. mkdir lib
    1. Put PgsLookAndFeel.jar in lib
    1. Put sqlitejdbc-v054-pure.jar in lib
  1. mkdir -p tmp/hoipolloi
    1. Put all contents of the images trunk in tmp
    1. Put all compiled class files in tmp/hoipolloi
  1. mkdir pictures
    1. Copy tmp/249.jpg to pictures/1.jpg
    1. Copy tmp/3.jpg to pictures/2.jpg
    1. Put unknown.jpg in pictures
    1. Pictures directory should only contain 1.jpg, 2.jpg and unknown.jpg
  1. Put the empty HP database file (pmp.s3db) into build directory.
  1. Put man.txt into build directory.
    1. The splash image should be correct set to the filename in tmp/ which is splashalpha.jpg right now.
    1. The sqlitejdbc in man.txt should be set to the right file in lib/
  1. Put hp.properties into build directory.
    1. Edit hp.properties and increment the version.
    1. The lastprofile should be 1, dbfile should be pmp.s3db
  1. Put build.txt into build directory as build.sh
  1. Make build.sh executable (chmod +x build.sh)
  1. Put Hoipolloi.bat and Hoipolloi.sh into build directory.
  1. Run ./build.sh
  1. Upload HoiPolloi.zip to GoogleCode (after testing of course)