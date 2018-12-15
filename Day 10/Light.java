import java.util.regex.Pattern;
import java.util.regex.Matcher;
public class Light
{
	private static final Pattern LIGHT_REGEX = Pattern.compile("position=<\\s*([-]{0,1}[0-9]+),\\s*([-]{0,1}[0-9]+)> velocity=<\\s*([-]{0,1}[0-9]+),\\s*([-]{0,1}[0-9]+)>");

	private Point position;
	private Velocity velocity;

	public Light(Point initialPosition, Velocity velocity)
	{
		this.position = initialPosition;
		this.velocity = velocity;
	}

	public void passTime(int seconds)
	{
		// Need to update the point
		position.x = position.x + seconds * velocity.getVx();
		position.y = position.y + seconds * velocity.getVy();
	}

	public Point getPosition()
	{
		return position;
	}

	public static Light parse(String lightText)
	{
		Light light = null;
		Matcher matcher = LIGHT_REGEX.matcher(lightText);

		if (matcher.matches())
		{
			Point p = new Point();
			p.x = Integer.parseInt(matcher.group(1));
			p.y = Integer.parseInt(matcher.group(2));

			Velocity v = new Velocity(Integer.parseInt(matcher.group(3)), Integer.parseInt(matcher.group(4)));

			light = new Light(p, v);
		}

		return light;
	}
}
