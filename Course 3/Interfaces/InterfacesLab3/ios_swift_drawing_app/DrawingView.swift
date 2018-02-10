

import UIKit

class DrawingView: UIView {
	
	var drawColor = UIColor.black
	var lineWidth: CGFloat = 10
	
	private var lastPoint: CGPoint!
	private var bezierPath: UIBezierPath!
	private var pointCounter: Int = 0
	private let pointLimit: Int = 128
	private var preRenderImage: UIImage!
	
	
	override init(frame: CGRect) {
		super.init(frame: frame)
		
		initBezierPath()
	}

	required init?(coder aDecoder: NSCoder) {
		super.init(coder: aDecoder)
		
		initBezierPath()
	}
	
	func initBezierPath() {
		bezierPath = UIBezierPath()
		bezierPath.lineCapStyle = CGLineCap.round
		bezierPath.lineJoinStyle = CGLineJoin.round
	}
	

	
	override func touchesBegan(_ touches: Set<UITouch>, with event: UIEvent?) {
		let touch: AnyObject? = touches.first
		lastPoint = touch!.location(in: self)
		pointCounter = 0
	}
	
	override func touchesMoved(_ touches: Set<UITouch>, with event: UIEvent?) {
		let touch: AnyObject? = touches.first
		let newPoint = touch!.location(in: self)
		
		bezierPath.move(to: lastPoint)
		bezierPath.addLine(to: newPoint)
		lastPoint = newPoint
		
		pointCounter += 1
		
		if pointCounter == pointLimit {
			pointCounter = 0
			renderToImage()
			setNeedsDisplay()
			bezierPath.removeAllPoints()
		}
		else {
			setNeedsDisplay()
		}
	}
	
	override func touchesEnded(_ touches: Set<UITouch>, with event: UIEvent?) {
		pointCounter = 0
		renderToImage()
		setNeedsDisplay()
		bezierPath.removeAllPoints()
	}
	
	override func touchesCancelled(_ touches: Set<UITouch>?, with event: UIEvent?) {
		touchesEnded(touches!, with: event)
	}
	
	
	
	func renderToImage() {
		
		UIGraphicsBeginImageContextWithOptions(self.bounds.size, false, 0.0)
		if preRenderImage != nil {
			preRenderImage.draw(in: self.bounds)
		}
		
		bezierPath.lineWidth = lineWidth
		drawColor.setFill()
		drawColor.setStroke()
		bezierPath.stroke()
		
		preRenderImage = UIGraphicsGetImageFromCurrentImageContext()
		
		UIGraphicsEndImageContext()
	}
	
	
	
	override func draw(_ rect: CGRect) {
		super.draw(rect)
		
		if preRenderImage != nil {
			preRenderImage.draw(in: self.bounds)
		}
		
		bezierPath.lineWidth = lineWidth
		drawColor.setFill()
		drawColor.setStroke()
		bezierPath.stroke()
	}

	
	func clear() {
		preRenderImage = nil
		bezierPath.removeAllPoints()
		setNeedsDisplay()
	}
	

	func hasLines() -> Bool {
		return preRenderImage != nil || !bezierPath.isEmpty
	}

}
