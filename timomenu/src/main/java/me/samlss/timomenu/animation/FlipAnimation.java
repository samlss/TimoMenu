package me.samlss.timomenu.animation;

import android.graphics.Camera;
import android.graphics.Matrix;
import android.view.animation.Animation;
import android.view.animation.Transformation;

/**
 * @author SamLeung
 * @e-mail samlssplus@gmail.com
 * @github https://github.com/samlss
 * @description Flip-p-p-p animation...
 */
class FlipAnimation extends Animation{
	private float centerX ;
	private float centerY ;

	private float fromDegree ;
	private float toDegree ;
	
	private int duration ;
	private Camera camera = new Camera() ;
	private boolean needToReduce;
	private float decayFactor;
	private float lastInterpolatedTime = 0;

	public FlipAnimation(float fromDegree, float toDegree , float x, float y, int duration, boolean needToReduce)
	{
		this.centerX = x ;
		this.centerY = y ;
		
		this.duration = duration ;
		this.needToReduce = needToReduce ;
		
		this.fromDegree = fromDegree ;
		this.toDegree = toDegree ;
		
		float abs = Math.abs(fromDegree) < Math.abs(toDegree) ? Math.abs(fromDegree) : Math.abs(toDegree) ;
		decayFactor = abs / 3 + 1 ;
	}
	
	@Override
	public void initialize(int width, int height, int parentWidth,
			int parentHeight) {
		super.initialize(width, height, parentWidth, parentHeight);
		setDuration(duration) ;

		if(needToReduce)
		{
			setRepeatCount(5) ;
			setRepeatMode(Animation.REVERSE);
		}
	}

	@Override
	protected void applyTransformation(float interpolatedTime, Transformation t) {
		float degrees = fromDegree + (toDegree - fromDegree)  * interpolatedTime;
		Matrix matrix = t.getMatrix();   
		
		boolean equals = false;
		if(Math.abs(fromDegree) == Math.abs(toDegree))
			equals = true ;
		
	    camera.save();   
		if((interpolatedTime != lastInterpolatedTime) && needToReduce)
		{	
			if(degrees == toDegree)
			{
				if(fromDegree > 0)
				{
					if(!equals)
						fromDegree = -toDegree ;
					fromDegree-= decayFactor;
					if(fromDegree < 0)
						fromDegree = 0 ;
				}
				else
				{
					if(!equals)
						fromDegree = -toDegree ;
					fromDegree+= decayFactor;
					
					if(fromDegree > 0)
						fromDegree = 0 ;
				}
			}
			if(degrees == fromDegree)
			{
				if(toDegree > 0)
				{
					toDegree-= decayFactor;
					if(toDegree < 0)
						toDegree = 0 ;
				}
				else
				{
					toDegree+= decayFactor;
					if(toDegree > 0)
					{
						toDegree = 0 ;
					}
				}
			}
		}
		
		camera.rotateY(degrees) ;   
		camera.getMatrix(matrix) ;  
		camera.restore() ;   
		
		matrix.preTranslate(-centerX, -centerY) ;
		matrix.postTranslate(centerX, centerY) ;
		lastInterpolatedTime = interpolatedTime ;
	}

}
