var jsfunc = function(image) {

var height = image.getHeight();
var width = image.getWidth();

  for(var i=0; i < height; i++)
  {
    for(var j=0; j < width; j++)
    {
      var color = new java.awt.Color(image.getRGB(i, j));
      var R = color.getRed()
      var G = color.getGreen()
      var B = color.getBlue()

      if( R - 35 < 0) R = 0
      else R = R - 35

      if( G -35 < 0) G = 0
      else G = G - 35

      if( B + 35 > 255) B = 255
      else B = B + 35

      var newColor = new java.awt.Color(R.intValue(), G.intValue(), B.intValue());
      image.setRGB(i, j, newColor.getRGB());
    }
  }
  return image;
};