function compareNumbers(a,b){
    return a - b
}
var jsfunc = function(image) {

var height = image.getHeight();
var width = image.getWidth();

var pixelColor = new Array(9);

var Rarr = new Array(9);
var Garr = new Array(9);
var Barr = new Array(9);

  for(var i=1; i < height-1; i++)
  {
    for(var j=1; j < width-1; j++)
    {

        pixelColor[0] = new java.awt.Color(image.getRGB(i-1,j-1));
        pixelColor[1] = new java.awt.Color(image.getRGB(i-1,j));
        pixelColor[2] = new java.awt.Color(image.getRGB(i-1,j+1));
        pixelColor[3] = new java.awt.Color(image.getRGB(i,j+1));
        pixelColor[4] = new java.awt.Color(image.getRGB(i+1,j+1));
        pixelColor[5] = new java.awt.Color(image.getRGB(i+1,j));
        pixelColor[6] = new java.awt.Color(image.getRGB(i+1,j-1));
        pixelColor[7] = new java.awt.Color(image.getRGB(i,j-1));
        pixelColor[8] = new java.awt.Color(image.getRGB(i,j));


        for(var k=0;k<9;k++){
            Rarr[k]=pixelColor[k].getRed();
            Garr[k]=pixelColor[k].getGreen();
            Barr[k]=pixelColor[k].getBlue();
        }

        //Rarr.sort(compareNumbers);
       // Garr.sort(compareNumbers);
        //Barr.sort(compareNumbers);
  
      var newColor = new java.awt.Color(Rarr[4].intValue(), Garr[4].intValue(), Barr[4].intValue());
      image.setRGB(i, j, newColor.getRGB());
    }
  }
  return image;
};