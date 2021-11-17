var config = {
    type: Phaser.AUTO,
    width: 800,
    height: 600,
    scene: {
        preload: preload,
        create: create,
        update: update
    }
};

var game = new Phaser.Game(config);

function preload(){
    this.load.image('tile', 'assets/tile.jpg');
}

function create(){
    this.image = this.add.image(2000, 2000, 'tile');
}

function update(){

}