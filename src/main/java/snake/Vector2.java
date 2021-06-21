package snake;

public class Vector2 
{
	private int x, y;
	
	public static Vector2 LEFT  = new Vector2(-1, 0);
	public static Vector2 RIGHT = new Vector2( 1, 0);
	public static Vector2 UP    = new Vector2( 0,-1);
	public static Vector2 DOWN  = new Vector2( 0, 1);
	
	public Vector2(int x, int y) 
	{
		this.x = x;
		this.y = y;
	}
	
	public Vector2(Vector2 v)
	{
		this.x = v.x;
		this.y = v.y;
	}
	
	public Vector2 add(Vector2 v)
	{
		return new Vector2(this.x + v.x, this.y + v.y);
	}
	
	public Vector2 scalar(int k)
	{
		return new Vector2(k * this.x, k * this.y);
	}

	public int getX() 
	{
		return x;
	}

	public void setX(int x) 
	{
		this.x = x;
	}

	public int getY() 
	{
		return y;
	}

	public void setY(int y) 
	{
		this.y = y;
	}
	
	@Override
	public boolean equals(Object obj)
	{
		
		return this.x == ((Vector2)obj).x && this.y == ((Vector2)obj).y; 
	}
}
