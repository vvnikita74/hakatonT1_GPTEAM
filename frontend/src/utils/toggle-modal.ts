export default function toggleModal(modalID = '') {
	document.getElementById(modalID)?.classList?.toggle('opened')
}
