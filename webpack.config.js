var path = require('path');

module.exports = {
    entry: './src/main/webapp/js/App.js',
    output: {
        path: __dirname,
        filename: './src/main/resources/static/built/bundle.js'
    },
	module: {
        loaders: [
            {
                test: /\.js$/,
                exclude: /node_modules/,
                loader: 'babel-loader',
                query: {
                    presets: ['env', 'react']
                }
            }, {
				test: /\.css$/,
				use: [ 'style-loader', 'css-loader' ]
			}
        ]
    }
};
