package ua.tqs.projects.individual.utils;

public class RethrowException extends RuntimeException
{
	private static final long serialVersionUID = 4335514678899570757L;

	public RethrowException()
	{
	}

	public RethrowException(String arg0)
	{
		super(arg0);
	}

	public RethrowException(Throwable arg0)
	{
		super(arg0);
	}

	public RethrowException(String arg0, Throwable arg1)
	{
		super(arg0, arg1);
	}

	public RethrowException(String arg0, Throwable arg1, boolean arg2, boolean arg3)
	{
		super(arg0, arg1, arg2, arg3);
	}
}
