// package frc.robot.Subsystems;
// import edu.wpi.first.wpilibj.AddressableLED;
// import edu.wpi.first.wpilibj.AddressableLEDBuffer;

// public class LazyLights {
//     //JK work in progress
//     //For Controlling WS2812b LEDs without a blinkin

//     private int LAZY_LED_PORT = 3; // MUST BE PWM **NOT** ON MXP
//     private int LAZY_LED_LENGTH = 60;

//     public enum LazyLightsColor {
//         Red, Blue, Green, Rainbow
//     }

//     private AddressableLED lazyLeds = new AddressableLED(LAZY_LED_PORT); // MUST BE PWM **NOT** ON MXP
//     private AddressableLEDBuffer lazyLedBuffer = new AddressableLEDBuffer(LAZY_LED_LENGTH);

//     public void createLazyLights() {
//         lazyLeds.setLength(lazyLedBuffer.getLength());
//         lazyLeds.setData(lazyLedBuffer);
//         lazyLeds.setBitTiming(900, 350, 370, 900);
//         lazyLeds.setSyncTime(20);
//         lazyLeds.start();
//     }

//     public void setLazyLights(LazyLightsColor lazyColor) {
//         if (lazyColor == LazyLightsColor.Blue) {
//             for (var i = 0; i < lazyLedBuffer.getLength(); i++) {
//                 // Sets the specified LED to the HSV values for Blue
//                 lazyLedBuffer.setHSV(i, 210, 93, 66);
//                 lazyLeds.setData(lazyLedBuffer);
//             }
//         }
//         if (lazyColor == LazyLightsColor.Red) {
//             for (var i = 0; i < lazyLedBuffer.getLength(); i++) {
//                 // Sets the specified LED to the HSV values for red
//                 lazyLedBuffer.setRGB(i, 150, 0, 0);
//                 // lazyLeds.setData(lazyLedBuffer);
//             }
//             lazyLeds.setData(lazyLedBuffer);
//         }
//         if (lazyColor == LazyLightsColor.Green) {
//             for (var i = 0; i < lazyLedBuffer.getLength(); i++) {
//                 // Sets the specified LED to the HSV values for Green
//                 lazyLedBuffer.setHSV(i, 112, 91, 58);
//                 lazyLeds.setData(lazyLedBuffer);
//             }
//         }
//         if (lazyColor == LazyLightsColor.Rainbow) {
//             calcLazyRainbow();
//             // Set the LEDs
//             lazyLeds.setData(lazyLedBuffer);
//         }
//     }


//     private int m_rainbowFirstPixelHue;
//     private void calcLazyRainbow() {
//         // For every pixel
//         for (var i = 0; i < lazyLedBuffer.getLength(); i++) {
//             // Calculate the hue - hue is easier for rainbows because the color
//             // shape is a circle so only one value needs to precess
//             int hue = (m_rainbowFirstPixelHue + (i * 180 / lazyLedBuffer.getLength())) % 180;
//             // Set the value
//             lazyLedBuffer.setHSV(i, hue, 255, 128);
//         }
//     }

//     private void moveLazyRainbow() {
//         m_rainbowFirstPixelHue += 3;
//         m_rainbowFirstPixelHue %= 180;
//     }
// }

// /**
//  * Gets the current display pattern of the LEDs.
//  * 
//  * @return a number between -1 and 1 corresponding to the current pattern
//  */
// // public double getLEDs() {
// // return color;
// // }
// // Reuse buffer
// // Default to a length of 60, start empty output
// // Length is expensive to set, so only set it once, then just update data

// // Set the data
// // m_led.setData(m_ledBuffer);
// // m_led.start();
// // }

// // }
