var path = require('path');

module.exports = {
    entry: './src/main/js/app.js',
    devtool: "source-map",
    cache: true,
    mode: 'development',
    output: {
        path: __dirname,
        filename: './src/main/resources/static/built/bundle.js'
    },
    module: {
        rules: [
            {
                test: /\.css$/,         // Target CSS files
                use: [
                    'style-loader',      // Injects CSS into the DOM
                    'css-loader'         // Interprets CSS as modules
                ]
            },
            {
                test: path.join(__dirname, '.'),
                exclude: /(node_modules)/,
                use: [{
                    loader: 'babel-loader',
                    options: {
                        presets: ["@babel/preset-env", "@babel/preset-react"]
                    }
                }]
            }
        ]
    }
};