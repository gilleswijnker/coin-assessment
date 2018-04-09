var path = require('path');

module.exports = {
    entry: './src/main/webapp/js/app.js',
    output: {
        path: __dirname,
        filename: './src/main/resources/static/built/bundle.js'
    },
	module: {
        loaders: [
            {
                test: path.join(__dirname, '.'),
                exclude: /node_modules/,
                loader: 'babel-loader',
                query: {
                    presets: ['env', 'react']
                }
            }
        ]
    }
};