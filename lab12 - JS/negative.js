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

      R = 255 - R;
      G = 255 - G;
      B = 255 - B

      var newColor = new java.awt.Color(R.intValue(), G.intValue(), B.intValue());
      image.setRGB(i, j, newColor.getRGB());
    }
  }
  return image;
};