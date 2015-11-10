package com.kevinmdunne.chess.ui.pieces;

import javax.media.j3d.Appearance;
import javax.media.j3d.Material;

import com.kevinmdunne.chess.model.Piece;
import com.kevinmdunne.chess.ui.ISelectable;
import com.sun.j3d.utils.geometry.Cylinder;
import com.sun.j3d.utils.geometry.Primitive;

public class PawnUI extends Cylinder implements PieceUI,ISelectable{

	private Material material;
	private Piece piece;
	
	public PawnUI(Piece piece){
		super(0.04f, 0.08f,Primitive.GENERATE_NORMALS + Primitive.GENERATE_TEXTURE_COORDS,null);
		
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

	@Override
	public boolean isWhite() {
		return piece.isWhite();
	}

	@Override
	public Piece getModelledObject() {
		return this.piece;
	}
}
