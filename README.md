# eiti-eiasr (under construction)

## Goal
Main goal of project is enabling tranffic signs recognition utilizing own implementation of SVM (Support Vector Machine) algorithm running on HOG features (Histograms of Oriented Gradients). Approach is to be implemented in OpenCV libraries for Java. Ready artefact (like opencv HOGDescrptor) or pretrained models are not to be used.

## Resources

### OpenCV for Java

Building OpenCV artefacts for Java is done according to [tutorial](http://opencv-java-tutorials.readthedocs.io/en/latest/01-installing-opencv-for-java.html#install-opencv-3-x-under-linux)

Unfortutely documentation and community around OpenCV in Java is not as widely available as in case of C++ or Python. Lot of details have to be figured about combining information from OpenCV javadoc and C++ documentation.

### Source of images
In project images of traffic sign are used. There are sets of images available online in framework of INI Benchmark
project. Images are splitted into two datasets data [GTSRB](http://benchmark.ini.rub.de/?section=gtsrb&subsection=news) (for classification) and [GTSDB](http://benchmark.ini.rub.de/?section=gtsdb&subsection=news) (for localization).
<p align="center"> <img  src="/doc/img/Selection_001.png"></p>

### References
The best reference for undersanding Histogram of Oriented Gradients is original [paper by Dalal and Triggs](https://lear.inrialpes.fr/people/triggs/pubs/Dalal-cvpr05.pdf). It gives holistic view on HOG ad explains why and how.

Second, easier resource is [article by Satya Mallick](https://www.learnopencv.com/histogram-of-oriented-gradients/) - not everything is said straightfully, but author gives good tips about the way to vizualize HOGs (this has been implemented)

## HOG descriptor

<img align="right" height=230 src="/doc/img/Selection_004.png">

First challenge working with Java OpenCV is that there is no native methods to even display simple image (openCV Image or Mat object). Utilities to display images and visualize features are implemented from scratch in [DisplayUtils class](/src/eiasr/DisplayUtils.java).

Calculatig HOG descriptor consists of few steps:

#### Preprocessing

For now only preprocessign is resizing input images to the same size 64x64 pixels. It is be determined if using additinal preprocessing (e.g. blurring) would increase effectiveness of utilizing HOGs.
 
<img align="right" height=230 src="/doc/img/Selection_002.png">

#### Gradient calcualations

For every pixel in 64x64 image gradient magintude and direction (angle) is calculated
  
#### Splitting Image into cells

Image is divided into 8x8 pixels cells, what give 8 cells in each row and column per image.

#### Building Histogram for every Cell

Having calculated gradient in every cell, histograms are built. Histogram consists of 18 bins gor gradient direction (360 degrees divided with interval of 20 degrees). <img align="right" height=230 src="/doc/img/Selection_003.png"> Basing on gradient direction in given pixel magnitude is splited and added to pratcular bins. Similiar histogram is build for every cell.

#### Histogram Normalization in Blocks of Cells

Acorrding to original paper introducing HOG normalization is performed in block 16x16 pixels (so 2x2 cells)

#### Building actual HOG Descriptor (vector)

Most of implemented methods are fully paramtrized so HOG descriptor can be defined in many ways. Considering above number (they seem to be rational for given iamges of traffic signes) our HOG descriptor is to have length of 3528 values. 

Where this number come from? In 64x64 image we can move 16x16 block 7 times vertically and 7 times horizontally (moving by 1 cell = 8 pixels). In every block we have 4 cells. Every cell has 18 bins in its historgram. 

That gives us 7 * 7 * 4 * 18 = 3528.

On below picture (right) visualization of HOG is shown. In every cell we have all bin presented as lines. Line length is proportional to bin value. It is visible that dominant direction of the histogram captures the oval shape of the sign and shape of numbers.

<p align="center"><img height=400 src="/doc/img/Selection_005.png"><img height=400 src="/doc/img/Selection_006.png"></p>

For comparison here are visualizations of HOGs for the same image but scaled to 128x128 and 32x32 before computations
<p align="center"><img height=400 src="/doc/img/Selection_007.png"><img height=400 src="/doc/img/Selection_008.png"></p>

*Additinal note:* Some more complicated methods are covered with unit tests ([/test/eiasr/](/test/eiasr))
