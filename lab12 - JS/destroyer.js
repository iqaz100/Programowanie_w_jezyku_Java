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

      R = (Math.random()* (255+1))
      G = (Math.random()* (255+1))
      B = (Math.random()* (255+1))

      var newColor = new java.awt.Color(R.intValue(), G.intValue(), B.intValue());
      image.setRGB(i, j, newColor.getRGB());
    }
  }
  return image;
};