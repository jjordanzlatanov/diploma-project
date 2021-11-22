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
    this.load.image('tile', 'assets/tile.png');
}

function create(){
    this.image = this.add.image(82, 90, 'tile');
}

function update(){
    this.image.x += 1;
}