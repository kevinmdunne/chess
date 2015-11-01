package com.kevinmdunne.chess.ui;

import javax.media.j3d.Appearance;
import javax.media.j3d.Material;

import com.kevinmdunne.chess.model.Piece;
import com.sun.j3d.utils.geometry.Cone;
import com.sun.j3d.utils.geometry.Primitive;

public class PieceUI extends Cone implements ISelectable{
	
	private Cone shape;
	private Material material;
	private Piece piece;
	
	public PieceUI(Piece piece){
		super(0.06f, 0.1f,Primitive.GENERATE_NORMALS + Primitive.GENERATE_TEXTURE_COORDS,null);
		
		this.piece = piece;
		Appearance appearance = new Appearance();

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
	
	public Primitive getShape(){
		return this.shape;
	}

}
