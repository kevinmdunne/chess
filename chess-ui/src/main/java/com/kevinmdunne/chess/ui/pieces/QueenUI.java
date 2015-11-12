package com.kevinmdunne.chess.ui.pieces;

import java.awt.Container;
import java.net.URL;

import javax.media.j3d.Appearance;
import javax.media.j3d.Material;
import javax.media.j3d.Texture;
import javax.media.j3d.TextureAttributes;
import javax.vecmath.Color4f;

import com.kevinmdunne.chess.model.Piece;
import com.kevinmdunne.chess.ui.ISelectable;
import com.kevinmdunne.chess.ui.SpaceUI;
import com.sun.j3d.utils.geometry.Box;
import com.sun.j3d.utils.geometry.Primitive;
import com.sun.j3d.utils.image.TextureLoader;

public class QueenUI extends Box implements PieceUI,ISelectable{
	
	private static final URL WHITE_TEXTURE_PATH = SpaceUI.class.getResource("/images/white_queen.png");
	private static final URL BLACK_TEXTURE_PATH = SpaceUI.class.getResource("/images/black_queen.png");
	
	private Material material;
	private Piece piece;
	
	public QueenUI(Piece piece){
		super(0.04f, 0.10f,0.04f,Primitive.GENERATE_NORMALS + Primitive.GENERATE_TEXTURE_COORDS,null);
		
		this.piece = piece;
		Appearance appearance = new Appearance();

		URL path = BLACK_TEXTURE_PATH;
		if(piece.isWhite()){
			path = WHITE_TEXTURE_PATH;
		}
		
		TextureLoader loader = new TextureLoader(path, new Container());
		Texture texture = loader.getTexture();
		texture.setBoundaryModeS(Texture.CLAMP);
		texture.setBoundaryModeT(Texture.CLAMP);
		texture.setBoundaryColor(new Color4f(0.0f, 1.0f, 0.0f, 0.0f));

		TextureAttributes texAttr = new TextureAttributes();
		texAttr.setTextureMode(TextureAttributes.DECAL);

		appearance.setTexture(texture);
		appearance.setTextureAttributes(texAttr);
		
		this.material = new Material();
		this.material.setCapability(Material.ALLOW_COMPONENT_WRITE);
		if(piece.isWhite())
			this.material.setDiffuseColor(1.0f, 1.0f, 1.0f);
		else
			this.material.setDiffuseColor(0.0f, 0.0f, 0.0f);
		
		appearance.setMaterial(this.material);
		this.setAppearance(appearance);
	}
	
	@Override
	public void select(){
		this.material.setDiffuseColor(0.0f, 0.0f, 1.0f);
	}
	
	@Override
	public void deselect(){
		if(piece.isWhite())
			this.material.setDiffuseColor(1.0f, 1.0f, 1.0f);
		else
			this.material.setDiffuseColor(0.0f, 0.0f, 0.0f);
	}

	@Override
	public boolean isWhite() {
		return piece.isWhite();
	}

	@Override
	public Piece getModelledObject() {
		return this.piece;
	}
	
	@Override
	public float getYOffset() {
		return 0.09f;
	}
}
