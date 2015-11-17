package com.kevinmdunne.chess.ui;

import java.awt.Container;
import java.net.URL;

import javax.media.j3d.Appearance;
import javax.media.j3d.Material;
import javax.media.j3d.Texture;
import javax.media.j3d.TextureAttributes;
import javax.vecmath.Color4f;

import com.sun.j3d.utils.geometry.Box;
import com.sun.j3d.utils.geometry.Primitive;
import com.sun.j3d.utils.image.TextureLoader;

public class SpaceUI extends Box implements ISelectable{

	private static final URL WHITE_TEXTURE = SpaceUI.class.getResource("/images/white.png");
	private static final URL BLACK_TEXTURE = SpaceUI.class.getResource("/images/black.png");
	
	private Material material;
	private Appearance appearance;
	
	private int x;
	private int y;
	private boolean white;
	
	public SpaceUI(int x,int y,boolean white){
		super(0.08f, 0.012f, 0.08f, Primitive.GENERATE_NORMALS + Primitive.GENERATE_TEXTURE_COORDS,null);
		
		this.x = x;
		this.y = y;
		this.white = white;
		
		URL path = WHITE_TEXTURE;
		if(!white){
			path = BLACK_TEXTURE;
		}
		
		appearance = new Appearance();
		
		TextureLoader loader = new TextureLoader(path, new Container());
		Texture texture = loader.getTexture();
		texture.setBoundaryModeS(Texture.WRAP);
		texture.setBoundaryModeT(Texture.WRAP);
		texture.setBoundaryColor(new Color4f(0.0f, 1.0f, 0.0f, 0.0f));

		TextureAttributes texAttr = new TextureAttributes();
		texAttr.setTextureMode(TextureAttributes.MODULATE);

		appearance.setTexture(texture);
		appearance.setTextureAttributes(texAttr);

		material = new Material();
		material.setCapability(Material.ALLOW_COMPONENT_WRITE);
		material.setSpecularColor(0.2f, 0.2f, 0.2f); // reduce default values
		appearance.setMaterial(material);
		
		this.setAppearance(appearance);
	}
	
	public int getX(){
		return this.x;
	}
	
	public int getY(){
		return this.y;
	}
	
	public void select(){
		this.material.setDiffuseColor(0.0f, 0.5f, 1.0f);
	}
	
	public void deselect(){
		this.material.setDiffuseColor(1.0f, 1.0f, 1.0f);
	}
	
	public boolean isWhite(){
		return this.white;
	}
}
