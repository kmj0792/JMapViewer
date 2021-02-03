import java.awt.Color;
import java.awt.image.ColorModel;
import java.util.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;

public class EQParser implements Comparable<EQParser> {

	private static final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    
	public Date dateTime;
	public double magnitude;
	public double latitude;
	public double longitude;
	public String location;
	public String link;
	public Color magnitudeColor;

	public EQParser() {
		this(new Date(), 0, 0, 0, "");
	}
	
	public EQParser(Date dateTime, double magnitude, double latitude, double longitude, String location) {
        this.dateTime = dateTime;
        this.magnitude = magnitude;
        this.latitude = latitude;
        this.longitude = longitude;
        this.location = location;
        this.magnitudeColor = computeARGBColor();
	}
	
    // compute color based on magnitude
    public Color computeARGBColor() {
        int r = 0, g = 0, b = 0;
        if (this.magnitude <= 1.5)
        {
            // BLUE
            r = 0; g = 0; b = 255;
        }
        else if ((this.magnitude > 1.5) && (this.magnitude <= 2.75))
        {
            // BLUE->CYAN
            r = 0; g = (int)(255 * (this.magnitude - 1.5) / 1.25); b = 255;
        }
        else if ((this.magnitude > 2.75) && (this.magnitude <= 4.0))
        {
            // CYAN->GREEN
            r = 0; g = 255; b = 255 - (int)(255 * (this.magnitude - 2.75) / 1.25);
        }
        else if ((this.magnitude > 4.0) && (this.magnitude <= 5.25))
        {
            // GREEN->YELLOW
            r = (int)(255 * (this.magnitude - 4.0) / 1.25); g = 255; b = 0;
        }
        else if ((this.magnitude > 5.25) && (this.magnitude <= 6.5))
        {
            // YELLOW->RED
            r = 255; g = 255 - (int)(255 * (this.magnitude - 5.25) / 1.25); b = 0;
        }
        else if (this.magnitude > 6.5)
        {
            // RED
            r = 255; g = 0; b = 0;
        }
        return new Color(r, g, b);
    }
    
    @Override
    public String toString() {
    	return "" + formatter.format(dateTime) + "," + this.magnitude + "," + this.latitude + "," + this.longitude + "," + this.location;
    }
    
    @Override
    public boolean equals(Object other)                             // Object.equals overriding
    {
    	if (other instanceof KEarthquake) {
    		KEarthquake that = (KEarthquake) other;
    		return that.canEqual(this) 
    				&& this.dateTime.equals(that.dateTime) 
    				&& this.magnitude == that.magnitude
    				&& this.latitude == that.latitude
    				&& this.longitude == that.longitude
    				&& this.location == that.location;
    	}
    	return false;
    }

    @Override
    public int hashCode() {
    	return (dateTime.hashCode() + Double.hashCode(magnitude) + Double.hashCode(latitude) + Double.hashCode(longitude) + location.hashCode());
    }

    public boolean canEqual(Object other) {
    	return (other instanceof KEarthquake);
    }
    
    public int compareTo(KEarthquake other) {
    	return dateTime.compareTo(other.dateTime);
    }

}
