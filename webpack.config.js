const path = require('path');
const webpack = require('webpack');

module.exports = {
    resolve: {extensions: ['.js', '.jsx', '.ts', '.tsx'],},
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
                test: /\.tsx?$/,        // Target TypeScript files
                use: [
                    'ts-loader'          // Compiles TypeScript to JavaScript
                ],
                exclude: /node_modules/ // Exclude node_modules directory
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
    },
    plugins: [
        new webpack.ProvidePlugin({
            $: 'jquery',
            jQuery: 'jquery',
            'window.jQuery': 'jquery',
        }),
    ]
};