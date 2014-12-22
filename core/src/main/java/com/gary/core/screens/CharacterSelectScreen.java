package com.gary.core.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class CharacterSelectScreen implements Screen{

	private int numberOfPlayers = 0;
	Table table;
	
	public int getNumberOfPlayers() {
		return numberOfPlayers;
	}

	public void setNumberOfPlayers(int numberOfPlayers) {
		this.numberOfPlayers = numberOfPlayers;
	}

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resize(int width, int height) {
		Skin skin = new Skin( Gdx.files.internal( "defaultskin.json" ));
		Label titleLabel = new Label( "Choose your character!", skin );

		table = new Table();
		ImageButton imageBtn1 = new ImageButton(skin);
		ImageButton imageBtn2 = new ImageButton(skin);
		ImageButton imageBtn3 = new ImageButton(skin);
		ImageButton imageBtn4 = new ImageButton(skin);
		ImageButton imageBtn5 = new ImageButton(skin);
		ImageButton imageBtn6 = new ImageButton(skin);
		ImageButton imageBtn7 = new ImageButton(skin);
		ImageButton imageBtn8 = new ImageButton(skin);
		
		//create a table with 4 rows of 2 images. This will be duplicated for each player
        table.add(imageBtn1);
        table.add(imageBtn2);
    	table.row();
    	table.add(imageBtn3);
        table.add(imageBtn4);
    	table.row();
    	table.add(imageBtn5);
        table.add(imageBtn6);
    	table.row();
    	table.add(imageBtn7);
        table.add(imageBtn8);
    	table.row();
    	
    	//TODO position the tables based on the number of players
        if(numberOfPlayers == 2){	
        	
        }
        else if(numberOfPlayers == 3){	
               	
        }
        else if(numberOfPlayers == 4){	
           	
        }
		
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

}
